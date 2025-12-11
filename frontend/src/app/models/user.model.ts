export interface User {
  id: number;
  email: string;
  prenom: string;
  nom: string;
  role: UserRole;
  actif: boolean;
  createdAt: Date;
}

export enum UserRole {
  ADMIN = 'ADMIN',
  USER = 'USER'
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  token: string;
  type: string;
  id: number;
  email: string;
  prenom: string;
  nom: string;
  role: UserRole;
}
