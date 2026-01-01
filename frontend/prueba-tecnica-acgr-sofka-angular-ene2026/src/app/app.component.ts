import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Transaction, TransactionService } from './transactions.service';
import { EMPTY, Observable, Subject, timer, merge, of } from 'rxjs';
import { catchError, finalize, mapTo, shareReplay, switchMap, tap } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  private readonly fb = inject(FormBuilder);
  private readonly transactionService = inject(TransactionService);

  readonly transactionForm = this.fb.group({
    amount: [null as number | null, [Validators.required, Validators.min(1)]]
  });

  readonly transactions$: Observable<Transaction[]>;
  readonly refresher = new Subject<void>();

  listLoading = false;
  listError?: string;
  lastUpdated?: Date;

  creationPending = false;
  creationMessage?: string;
  creationError?: string;

  constructor() {
    const polling$ = timer(0, 5000).pipe(mapTo(undefined));
    const manualRefresh$ = this.refresher.pipe(mapTo(undefined));

    this.transactions$ = merge(polling$, manualRefresh$).pipe(
      switchMap(() => this.loadTransactions()),
      shareReplay({ bufferSize: 1, refCount: true })
    );
  }

  submitTransaction(): void {
    if (this.transactionForm.invalid) {
      this.transactionForm.markAllAsTouched();
      return;
    }

    const amount = Number(this.transactionForm.value.amount);
    this.creationPending = true;
    this.creationError = undefined;
    this.creationMessage = undefined;

    this.transactionService
      .create(amount)
      .pipe(
        tap((transaction) => {
          this.creationMessage = `Transaccion #${transaction.id} registrada`;
          this.transactionForm.reset();
          this.refreshNow();
        }),
        catchError((error) => {
          this.creationError = this.parseError(error);
          return EMPTY;
        }),
        finalize(() => {
          this.creationPending = false;
        })
      )
      .subscribe();
  }

  refreshNow(): void {
    this.refresher.next();
  }

  private loadTransactions(): Observable<Transaction[]> {
    this.listLoading = true;
    this.listError = undefined;

    return this.transactionService.list().pipe(
      tap(() => {
        this.lastUpdated = new Date();
      }),
      catchError((error) => {
        this.listError = this.parseError(error);
        return of([]);
      }),
      finalize(() => {
        this.listLoading = false;
      })
    );
  }

  private parseError(error: unknown): string {
    if (error instanceof Error) {
      return error.message;
    }

    return 'No fue posible contactar con el servicio de comisiones';
  }
}
