import { environment } from './environment';

describe('environment', () => {
  it('should expose defaults', () => {
    expect(environment.production).toBeFalse();
    expect(environment.apiBaseUrl).toBeDefined();
  });
});
