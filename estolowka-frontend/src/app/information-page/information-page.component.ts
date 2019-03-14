import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-information-page',
  templateUrl: './information-page.component.html',
  styleUrls: ['./information-page.component.css']
})
export class InformationPageComponent implements OnInit {

  constructor() {
  }

  ngOnInit() {
  }

  breakfastCost: string = '7zl';
  dinnerCost: string = '15zl';
  supperCost: string = '5zl';
}
