import { Component } from '@angular/core';
import { LogoutButton } from '../../../shared/components/logout-button/logout-button';

@Component({
  selector: 'app-header',
  imports: [LogoutButton],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {

}