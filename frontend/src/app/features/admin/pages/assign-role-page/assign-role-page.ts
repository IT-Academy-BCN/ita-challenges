import { Component, inject } from '@angular/core';
import { AdminApiService } from '../../data-access/admin-api.service';
import { Role } from '../../../../core/models/role.enum';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-assign-role-page',
  imports: [ReactiveFormsModule],
  templateUrl: './assign-role-page.html',
  styleUrl: './assign-role-page.css',
})
export class AssignRolePage {

  private readonly adminApiService = inject(AdminApiService)
  readonly fb = inject(FormBuilder)
  
  roleOptions = [
    { value: Role.MENTOR, label: 'Mentor' },
    { value: Role.STUDENT, label: 'Estudiant' }
  ];

  
  roleForm = this.fb.group({
    username: [''],
    role: ['']
  })

  onSubmit(){
      const {username, role} = this.roleForm.getRawValue()
      
      this.adminApiService.setUserRole(username as string, role as Role).subscribe({
        next: () => {
          this.roleForm.reset();
        },
      });
  }
}
