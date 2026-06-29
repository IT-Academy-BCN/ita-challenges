import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
	selector: 'app-login-button',
	imports:[],
	templateUrl: './login-button.html',
	styleUrl: './login-button.css',
})

export class LoginButton {
	private readonly router = inject(Router);

	login(): void{
		this.router.navigate(['/auth']);
	}
}
