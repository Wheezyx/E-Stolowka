import {Component, OnInit} from '@angular/core';
import {MenuService} from "../menu/menu.service";
import {PriceList} from "../menu/model/price-list";

@Component({
  selector: 'app-information-page',
  templateUrl: './information-page.component.html',
  styleUrls: ['./information-page.component.css']
})
export class InformationPageComponent implements OnInit {

  priceList: PriceList;

  constructor(private menuService: MenuService) {
    this.priceList = {"breakfastPrice":15.0,"dinnerPrice":13.0,"supperPrice":11.0,"updateDate":"2019-05-15"};
  }

  ngOnInit() {
    this.refresh();
  }

  getPriceList() {
    this.menuService.getMenuPrices().subscribe(data => {
      data.updateDate = data.updateDate.substring(0, 10);
      this.priceList = data;
    });
  }

  refresh() {
    this.getPriceList();
  }
}
