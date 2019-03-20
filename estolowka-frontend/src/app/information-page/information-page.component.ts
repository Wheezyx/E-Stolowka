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
  }

  ngOnInit() {
    this.refresh();
  }

  getPriceList() {
    this.menuService.getMenuPrices().subscribe(data => {
      this.priceList = data;
    });
  }

  refresh() {
    this.getPriceList();
  }
}
