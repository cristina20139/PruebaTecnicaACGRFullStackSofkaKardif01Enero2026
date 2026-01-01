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

  /**
   * 🤝 D + 🧹 Clean Code: Receives HttpClient via dependency injection so the service stays decoupled from transport details.
   * @param {HttpClient} http
   */
  constructor(private readonly http: HttpClient) {}

  /**
   * 📦 O + 🧹 Clean Code: Retrieves and sorts the latest transactions while keeping transformation logic localized for safe reuse.
   * @returns {Observable<Transaction[]>}
   */
  list(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(this.baseUrl).pipe(
      map((transactions) =>
        [...transactions].sort(
          (lhs, rhs) => new Date(rhs.executedAt).getTime() - new Date(lhs.executedAt).getTime()
        )
      )
    );
  }

  /**
   * 🧱 S + ✨ Clean Code: Posts a new transaction payload so this method's responsibility stays focused and easy to test.
   * @param {number} amount
   * @returns {Observable<Transaction>}
   */
  create(amount: number): Observable<Transaction> {
    return this.http.post<Transaction>(this.baseUrl, { amount });
  }
}
