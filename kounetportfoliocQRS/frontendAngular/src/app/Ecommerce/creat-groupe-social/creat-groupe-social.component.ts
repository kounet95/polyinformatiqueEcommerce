import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { SocialGroupDTO } from '../../mesModels/models';
import { CategoriesocialesService } from '../services/categoriesociales.service';

@Component({
  selector: 'app-creat-groupe-social',
  standalone: false,
  templateUrl: './creat-groupe-social.component.html',
  styleUrls: ['./creat-groupe-social.component.css']
})
export class CreatGroupeSocialComponent implements OnInit {
  socialgroupeForm: FormGroup;
  groupeSocial: SocialGroupDTO[] = [];
  successMessage?: string;
  errorMessage?: string;
  loading = false;
  constructor(
    private fb: FormBuilder,
    private categoriesocialesService: CategoriesocialesService,
  ) {
    this.socialgroupeForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      region: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]],
      pays: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(100)]]
    });
  }

  ngOnInit(): void {
   
  }

  onSubmit() {
    this.successMessage = undefined;
    this.errorMessage = undefined;

    if (this.socialgroupeForm.invalid) {
      this.socialgroupeForm.markAllAsTouched();
      return;
    }

    const socialGroup: SocialGroupDTO = {
      id: '', 
      name: this.socialgroupeForm.value.name,
      region: this.socialgroupeForm.value.region,
      pays: this.socialgroupeForm.value.pays
    };

    this.loading = true;
    this.categoriesocialesService.createSocialGroup(socialGroup).subscribe({
      next: () => {
        this.successMessage = 'Groupe social créé avec succès !';
        this.loading = false;
        this.socialgroupeForm.reset();
      },
      error: err => {
        this.errorMessage = "Erreur lors de la création du groupe social.";
        this.loading = false;
      }
    });
  }
}