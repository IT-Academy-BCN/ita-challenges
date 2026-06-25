import { Component, input, output } from '@angular/core';
import { ChallengeLanguage } from '../../../models/challenge-language.type';

@Component({
  selector: 'app-language-select-button',
  templateUrl: './language-select-button.html',
  styleUrl: './language-select-button.css',
})
export class LanguageSelectButton {
  language = input.required<ChallengeLanguage>();
  isActive = input<boolean>(false);
  clicked = output<ChallengeLanguage>();

  readonly languageLabels: Record<ChallengeLanguage, string> = {
    JAVA: 'Java',
    PHP: 'php',
    JAVASCRIPT: 'JavaScript',
    TYPESCRIPT: 'TypeScript',
    PYTHON: 'Python',
    SQL: 'SQL',
  };

  readonly languageIcons: Record<ChallengeLanguage, string> = {
    JAVA: 'assets/Java.svg',
    PHP: 'assets/PHP.svg',
    JAVASCRIPT: 'assets/JS.svg',
    TYPESCRIPT: 'assets/TS.svg',
    PYTHON: 'assets/Python.svg',
    SQL: 'assets/SQL.svg',
  };

  onClick() {
    this.clicked.emit(this.language());
  }
}