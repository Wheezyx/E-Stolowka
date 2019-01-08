export class Day {
    id: Number
    meals = [
      {name: 'Śniadanie', Selected: false},
      {name: 'Obiad', Selected: false},
      {name: 'Kolacja', Selected: false}
    ]
    selectedDate: String = ""
  
    constructor(id: number) {
      this.id = id;
    }
}