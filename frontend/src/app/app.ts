import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from './layout/components/header/header';
import { Sidebar } from './layout/components/sidebar/sidebar';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Header, Sidebar],
  templateUrl: './app.html',
})
export class App {}
