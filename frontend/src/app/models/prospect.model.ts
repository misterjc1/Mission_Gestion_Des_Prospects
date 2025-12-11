export interface Stats {
  totalProspects: number;
  nouveaux: number;
  contactes: number;
  relances: number;
  signes: number;
  perdus: number;
  montantTotalSigne: number;
  tauxConversion: number;
  parSecteur: { [key: string]: number };
}
