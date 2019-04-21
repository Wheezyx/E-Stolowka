import {MealType} from "./meal-type.enum";

export class Day {
    id: Number
    meals = [
      {type: MealType.BREAKFAST, Selected: false},
      {type: MealType.DINNER, Selected: false},
      {type: MealType.SUPPER, Selected: false}
    ]
    selectedDate: string = ""
  
    constructor(id: number) {
      this.id = id;
    }
}
