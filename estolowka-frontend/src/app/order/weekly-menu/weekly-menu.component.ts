import { Component, OnInit } from '@angular/core';
import { WeeklyMenuItem } from '../model/weekly-menu-item';
import { UploadService } from "../../upload/upload.service";

@Component({
  selector: 'app-weekly-menu',
  templateUrl: './weekly-menu.component.html',
  styleUrls: ['./weekly-menu.component.css']
})
export class WeeklyMenuComponent implements OnInit {
  showFiller = false;

  menuItems: WeeklyMenuItem[];
  /*menuItems: WeeklyMenuItem[] = [
    {date: "04/03/2019", breakfast: "Jajecznica z bekonem", dinner: "Spaghetti carbonara", supper: "Burger"},
    {date: "05/03/2019", breakfast: "Naleśniki amerykańskie", dinner: "Placki ziemniaczane z gulaszem wołowym", supper: "Tortilla"},
    {date: "06/03/2019", breakfast: "Owsianka", dinner: "Eskalopki", supper: "Tacosy"},
    {date: "07/03/2019", breakfast: "Parówki, kiełbaski", dinner: "Pierogi ruskie, z mięsem", supper: "Burrito"},
    {date: "08/03/2019", breakfast: "Tosty", dinner: "Łosoś grillowany", supper: "Krewetki"},
    {date: "09/03/2019", breakfast: "Jajka na miękko", dinner: "Pizza Pepperoni", supper: "Naleśniki z twarogiem"},
    {date: "10/03/2019", breakfast: "Croissant z dżemem", dinner: "Kotlet schabowy", supper: "Tatar"},
  ]*/

  constructor(private uploadService: UploadService) { }

  ngOnInit() {
  }

  getMenuItems() {
    this.uploadService.getMenu().subscribe(data => {
      this.menuItems = data;
    })
  }

}
