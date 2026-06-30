// @ts-ignore
import { RoleTranslatorPipe } from './role-translator.pipe';

describe('RoleTranslatorPipe', () => {
  let pipe: RoleTranslatorPipe;

  beforeEach(() => {
    pipe = new RoleTranslatorPipe();
  });

  it('create an instance', () => {
    expect(pipe).toBeTruthy();
  });

  it('should translate STUDENT to ESTUDIANT', () => {
    expect(pipe.transform('STUDENT')).toBe('ESTUDIANT');
  });

  it('should return the original value if translation is not found', () => {
    expect(pipe.transform('SUPERVISOR')).toBe('SUPERVISOR');
  });

  it('should return empty string if value is null or undefined', () => {
    expect(pipe.transform(undefined)).toBe('');
    expect(pipe.transform(null)).toBe('');
  });
});
