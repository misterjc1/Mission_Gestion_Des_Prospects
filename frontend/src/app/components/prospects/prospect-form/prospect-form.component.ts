import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterLink } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatIconModule } from '@angular/material/icon';
import { ProspectService, ProspectStatut, ProspectRequest } from '../../../services/prospect.service';

@Component({
  selector: 'app-prospect-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink, MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatSelectModule, MatIconModule],
  templateUrl: './prospect-form.component.html',
  styleUrls: ['./prospect-form.component.scss']
})
export class ProspectFormComponent implements OnInit {
  prospectForm: FormGroup;
  loading = false;
  isEditMode = false;
  prospectId?: number;
  statuts = Object.values(ProspectStatut);

  constructor(
    private fb: FormBuilder,
    private prospectService: ProspectService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.prospectForm = this.fb.group({
      nomEntreprise: ['', Validators.required],
      nomContact: ['', Validators.required],
      email: ['', [Validators.email]],
      telephone: [''],
      statut: [ProspectStatut.NOUVEAU, Validators.required],
      montantPotentiel: [0],
      secteurActivite: [''],
      notes: ['']
    });
  }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEditMode = true;
      this.prospectId = +id;
      this.loadProspect();
    }
  }

  loadProspect() {
    if (this.prospectId) {
      this.prospectService.getProspectById(this.prospectId).subscribe({
        next: (prospect) => {
          this.prospectForm.patchValue(prospect);
        }
      });
    }
  }

  onSubmit() {
    if (this.prospectForm.valid) {
      this.loading = true;
      const request: ProspectRequest = this.prospectForm.value;

      const operation = this.isEditMode && this.prospectId
        ? this.prospectService.updateProspect(this.prospectId, request)
        : this.prospectService.createProspect(request);

      operation.subscribe({
        next: () => {
          this.router.navigate(['/prospects']);
        },
        error: () => {
          this.loading = false;
        }
      });
    }
  }
}
