import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Prospect {
  id: number;
  nomEntreprise: string;
  nomContact: string;
  email: string;
  telephone: string;
  statut: ProspectStatut;
  montantPotentiel: number;
  secteurActivite: string;
  notes: string;
  createdByName: string;
  createdAt: Date;
}

export enum ProspectStatut {
  NOUVEAU = 'NOUVEAU',
  CONTACTE = 'CONTACTE',
  RELANCE = 'RELANCE',
  SIGNE = 'SIGNE',
  PERDU = 'PERDU'
}

export interface ProspectRequest {
  nomEntreprise: string;
  nomContact: string;
  email?: string;
  telephone?: string;
  statut: ProspectStatut;
  montantPotentiel?: number;
  secteurActivite?: string;
  notes?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ProspectService {
  private readonly API_URL = '/api/prospects';

  constructor(private http: HttpClient) {}

  getAllProspects(): Observable<Prospect[]> {
    return this.http.get<Prospect[]>(this.API_URL);
  }

  getProspectById(id: number): Observable<Prospect> {
    return this.http.get<Prospect>(`${this.API_URL}/${id}`);
  }

  createProspect(request: ProspectRequest): Observable<Prospect> {
    return this.http.post<Prospect>(this.API_URL, request);
  }

  updateProspect(id: number, request: ProspectRequest): Observable<Prospect> {
    return this.http.put<Prospect>(`${this.API_URL}/${id}`, request);
  }

  deleteProspect(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }
}
