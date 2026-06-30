import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'roleTranslator',
  standalone: true
})
export class RoleTranslatorPipe implements PipeTransform {
  private readonly translations: Record<string, string> = {
    'STUDENT': 'ESTUDIANT',
    'GUEST': 'CONVIDAT',
    'ADMIN': 'MENTOR'
  };

  transform(value: string | undefined | null): string {
    if (!value) {
      return '';
    }

    return this.translations[value.toUpperCase()] || value;
  }

}
