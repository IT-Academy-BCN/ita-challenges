import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { AdminApiService } from '../../data-access/admin-api.service';
import { Role } from '../../../../core/models/role.enum';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-assign-role-page',
  imports: [ReactiveFormsModule],
  templateUrl: './assign-role-page.html',
  styleUrl: './assign-role-page.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AssignRolePage {

  private readonly adminApiService = inject(AdminApiService)
  readonly fb = inject(FormBuilder)
  readonly isSubmitting = signal(false);
  readonly submitError = signal<string | null>(null);
  
  roleOptions = [
    { value: Role.MENTOR, label: 'Mentor' },
    { value: Role.STUDENT, label: 'Estudiant' }
  ];

  
  roleForm = this.fb.group({
    username: ['', [Validators.required, Validators.pattern('^[a-zA-Z0-9-]*$')]],
    role: ['', Validators.required]
  })

  onSubmit(){
    if(this.roleForm.valid){
      this.isSubmitting.set(true);
      this.submitError.set(null);

      const {username, role} = this.roleForm.getRawValue()
      
      this.adminApiService.setUserRole(username as string, role as Role).subscribe({
        next: () => {
          this.isSubmitting.set(false);
          this.roleForm.reset();
        },
        error: (err) => {
          this.isSubmitting.set(false);
          this.submitError.set('Error al assignar el rol. Prova-ho de nou.');
          console.error(err);
        }
      });
    }
  }
}
