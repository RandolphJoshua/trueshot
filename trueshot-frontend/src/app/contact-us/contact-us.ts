import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-contact-us',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './contact-us.html',
  styleUrls: ['./contact-us.css']   // <-- fix: styleUrls (plural)
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
