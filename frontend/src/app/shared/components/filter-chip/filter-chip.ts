import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-filter-chip',
  standalone: true,
  templateUrl: './filter-chip.html',
  styleUrl: './filter-chip.css',
})
export class FilterChipComponent {
  label = input.required<string>();
  active = input<boolean>(false);

  clicked = output<void>();
}
