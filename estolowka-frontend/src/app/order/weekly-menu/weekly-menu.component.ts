import { Component, OnInit } from '@angular/core';
import { WeeklyMenuItem } from '../model/weekly-menu-item';

@Component({
  selector: 'app-weekly-menu',
  templateUrl: './weekly-menu.component.html',
  styleUrls: ['./weekly-menu.component.css']
})
export class WeeklyMenuComponent implements OnInit {
  showFiller = false;

  menuItems: WeeklyMenuItem[] = [
    {dayName: "Poniedziałek", date: "04/03/2019", breakfast: "Jajecznica z bekonem", dinner: "Spaghetti carbonara", supper: "Burger"},
    {dayName: "Wtorek", date: "05/03/2019", breakfast: "Naleśniki amerykańskie", dinner: "Placki ziemniaczane z gulaszem wołowym", supper: "Tortilla"},
    {dayName: "Środa", date: "06/03/2019", breakfast: "Owsianka", dinner: "Eskalopki", supper: "Tacosy"},
    {dayName: "Czwartek", date: "07/03/2019", breakfast: "Parówki, kiełbaski", dinner: "Pierogi ruskie, z mięsem", supper: "Burrito"},
    {dayName: "Piątek", date: "08/03/2019", breakfast: "Tosty", dinner: "Łosoś grillowany", supper: "Krewetki"},
    {dayName: "Sobota", date: "09/03/2019", breakfast: "Jajka na miękko", dinner: "Pizza Pepperoni", supper: "Naleśniki z twarogiem"},
    {dayName: "Niedziela", date: "10/03/2019", breakfast: "Croissant z dżemem", dinner: "Kotlet schabowy", supper: "Tatar"},
  ]

  constructor() { }

  ngOnInit() {
  }

}
