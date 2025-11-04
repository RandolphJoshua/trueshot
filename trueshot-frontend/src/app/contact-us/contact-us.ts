import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { AppDetailsService } from '../services/app-details.service';

@Component({
  selector: 'app-contact-us',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './contact-us.html',
  styleUrls: ['./contact-us.css']   // <-- fix: styleUrls (plural)
})
export class ContactUs implements OnInit {
  sending = false;
  successMessage = '';
  errorMessage = '';

  // dynamic contact info (do NOT hardcode values here; start empty and load from API)
  supportEmail = '';
  supportPhone = '';
  hours = '';

  contact = {
    name: '',
    email: '',
    phone: '',
    message: ''
  };

  constructor(private appDetails: AppDetailsService) {}

  ngOnInit(): void {
    // accept a loose shape from the API (Partial<any>) so missing/extra optional fields won't cause TS errors
    this.appDetails.getActive().subscribe((d: any | null) => {
      this.supportEmail = d?.supportEmail ?? '';
      this.supportPhone = d?.supportNumber ?? '';
      this.hours = d?.hours ?? '';
    });
  }

  submit(form: NgForm): void {
    if (form.invalid) {
      form.control.markAllAsTouched();
      this.errorMessage = 'Please fix the highlighted fields.';
      return;
    }

    this.errorMessage = '';
    this.successMessage = '';
    this.sending = true;

    setTimeout(() => {
      this.sending = false;
      this.successMessage = 'Your message was sent! We’ll reply soon.';
      form.resetForm();
    }, 600);
  }
}
