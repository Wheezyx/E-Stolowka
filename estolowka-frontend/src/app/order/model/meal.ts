import {MealType} from "./meal-type.enum";

export class Meal {
  id: number;
  date: string;
  type: MealType;
  rate: number;
}
