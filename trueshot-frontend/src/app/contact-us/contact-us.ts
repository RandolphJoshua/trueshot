import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-contact-us',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './contact-us.html',
  styleUrl: './contact-us.css'
})
export class ContactUs {
  formData = {
    email: '',
    phone: '',
    message: ''
  };

  successMessage = '';

  submit(form: NgForm): void {
    this.successMessage = '';

    if (form.invalid) {
      return;
    }

    this.successMessage = 'Your message was sent successfully! We will get back to you soon.';
    form.resetForm();
  }
}
