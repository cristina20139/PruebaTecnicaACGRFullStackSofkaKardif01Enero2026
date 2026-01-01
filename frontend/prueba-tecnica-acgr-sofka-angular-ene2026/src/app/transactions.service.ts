import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';

export interface Transaction {
  id: number;
  amount: number;
  commission: number;
  executedAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private readonly baseUrl = 'http://localhost:8080/api/transactions';

  constructor(private readonly http: HttpClient) {}

  list(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(this.baseUrl).pipe(
      map((transactions) =>
        [...transactions].sort(
          (lhs, rhs) => new Date(rhs.executedAt).getTime() - new Date(lhs.executedAt).getTime()
        )
      )
    );
  }

  create(amount: number): Observable<Transaction> {
    return this.http.post<Transaction>(this.baseUrl, { amount });
  }
}
