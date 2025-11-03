import { Component } from '@angular/core';
import {MainHeader} from '../components/main-header/main-header';
import {Faqcomponent} from '../components/faqcomponent/faqcomponent';
import {FeaturedCameras} from '../components/featured-cameras/featured-cameras';

@Component({
  selector: 'app-home',
  imports: [
    MainHeader,
    Faqcomponent,
    FeaturedCameras
  ],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home {

}
