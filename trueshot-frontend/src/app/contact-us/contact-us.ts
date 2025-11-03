import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-contact-us',
  imports: [CommonModule, FormsModule],
  templateUrl: './contact-us.html',
  styleUrl: './contact-us.css'
})
export class ContactUs {
  sending = false;
  successMessage = '';
  errorMessage = '';

  contact = {
    name: '',
    email: '',
    phone: '',
    message: ''
  };

  submit(form: NgForm): void {
    if (form.invalid) {
      form.control.markAllAsTouched();
      this.errorMessage = 'Please fill in all the fields before sending your message.';
      return;
    }

    this.errorMessage = '';
    this.successMessage = '';
    this.sending = true;

    setTimeout(() => {
      this.sending = false;
      this.successMessage = 'Your message was sent! We will reply soon.';
      form.resetForm();
    }, 600);
  }
}
