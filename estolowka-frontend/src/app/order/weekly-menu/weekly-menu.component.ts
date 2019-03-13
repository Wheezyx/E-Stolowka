import { Component, OnInit } from '@angular/core';
import { WeeklyMenuItem } from '../../menu/model/weekly-menu-item';
import { MenuService } from "../../menu/menu.service";

@Component({
  selector: 'app-weekly-menu',
  templateUrl: './weekly-menu.component.html',
  styleUrls: ['./weekly-menu.component.css']
})
export class WeeklyMenuComponent implements OnInit {
  showFiller = false;
  isEmpty: boolean = true;

  menuItems: WeeklyMenuItem[];

  constructor(private menuService: MenuService) { }

  ngOnInit() {}

  getMenuItems() {
    this.menuService.getMenu().subscribe(data => {
      this.menuItems = data;
      if (data.length > 0) {
        this.isEmpty = false;
      }
    })
  }

}
