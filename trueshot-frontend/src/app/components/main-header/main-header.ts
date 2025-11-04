import { Component, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AppDetailsService } from '../../services/app-details.service';


@Component({
  selector: 'app-main-header',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './main-header.html',
  styleUrls: ['./main-header.css']
})
export class MainHeader {
  private readonly appDetails = inject(AppDetailsService);

  // expose observables and use async pipe in template to avoid ExpressionChangedAfterItHasBeenCheckedError
  readonly heroMessage$ = this.appDetails.getHeroMessage();
  readonly aboutContent$ = this.appDetails.getAboutContent();

}
