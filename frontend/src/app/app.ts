import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Header } from './layout/components/header/header';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, Header],
  templateUrl: './app.html',
})
export class App {}
