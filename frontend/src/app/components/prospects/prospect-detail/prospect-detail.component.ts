import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';
import { ProspectService, Prospect } from '../../../services/prospect.service';

@Component({
  selector: 'app-prospect-detail',
  standalone: true,
  imports: [CommonModule, RouterLink, MatCardModule, MatButtonModule, MatIconModule, MatChipsModule],
  templateUrl: './prospect-detail.component.html',
  styleUrls: ['./prospect-detail.component.scss']
})
export class ProspectDetailComponent implements OnInit {
  prospect?: Prospect;
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private prospectService: ProspectService
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.prospectService.getProspectById(+id).subscribe({
        next: (data) => {
          this.prospect = data;
          this.loading = false;
        },
        error: () => {
          this.loading = false;
          this.router.navigate(['/prospects']);
        }
      });
    }
  }
}
