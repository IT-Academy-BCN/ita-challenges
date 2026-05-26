import { Component, output } from '@angular/core';

@Component({
  selector: 'app-delete-button',
  standalone: true,
  imports: [],
  templateUrl: './delete-button.html',
  styleUrl: './delete-button.css',
})
export class DeleteButtonComponent {
  readonly deleteClick = output<void>();

  handleDelete(): void {
    this.deleteClick.emit();
  }
}