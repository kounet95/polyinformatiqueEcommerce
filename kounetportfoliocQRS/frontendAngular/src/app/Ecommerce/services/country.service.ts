import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';

export interface Country {
  name: string;
  code: string;
}

@Injectable({ providedIn: 'root' })
export class CountryService {
  constructor(private http: HttpClient) {}

  getCountries(): Observable<Country[]> {
    // Utilise l'API REST Countries
    return this.http.get<any[]>('https://restcountries.com/v3.1/all?fields=name,cca2')
      .pipe(
        // Map vers un tableau plus simple {name, code}
        map((data: any[]) => data.map((c: { name: { common: any; }; cca2: any; }) => ({
          name: c.name.common,
          code: c.cca2
        })).sort((a: { name: string; }, b: { name: any; }) => a.name.localeCompare(b.name)))
      );
  }
}