import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { ProspectService, Prospect, ProspectStatut } from '../../../services/prospect.service';

@Component({
  selector: 'app-prospect-list',
  standalone: true,
  imports: [CommonModule, RouterLink, MatCardModule, MatButtonModule, MatIconModule, MatChipsModule],
  templateUrl: './prospect-list.component.html',
  styleUrls: ['./prospect-list.component.scss']
})
export class ProspectListComponent implements OnInit {
  prospects: Prospect[] = [];
  loading = true;

  constructor(
    private prospectService: ProspectService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadProspects();
  }

  loadProspects() {
    this.prospectService.getAllProspects().subscribe({
      next: (data) => {
        this.prospects = data;
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }

  getStatutColor(statut: ProspectStatut): string {
    const colors: Record<ProspectStatut, string> = {
      [ProspectStatut.NOUVEAU]: 'primary',
      [ProspectStatut.CONTACTE]: 'accent',
      [ProspectStatut.RELANCE]: 'warn',
      [ProspectStatut.SIGNE]: '',
      [ProspectStatut.PERDU]: ''
    };
    return colors[statut] || '';
  }

  deleteProspect(id: number, event: Event) {
    event.stopPropagation();
    if (confirm('Voulez-vous vraiment supprimer ce prospect ?')) {
      this.prospectService.deleteProspect(id).subscribe({
        next: () => this.loadProspects()
      });
    }
  }

  navigateToDetail(id: number) {
    this.router.navigate(['/prospects', id]);
  }
}
