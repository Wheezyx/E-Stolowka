import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort } from '@angular/material';
import { User } from './model/user';
import { Pageable } from '../util/pageable';
import { UserService } from './service/user.service';
import { merge } from "rxjs";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  usersMock: User[] = [
    {id: 1, index: 123123, name: "Mateusz", surname: "Wedeł", email: "email@wp.pl", active: false},
    {id: 2, index: 123122, name: "Mateusz", surname: "Smagieł", email: "email@wp.pl", active: true},
    {id: 3, index: 223122, name: "Dawid", surname: "Majorczyk", email: "email@wp.pl", active: true},
    {id: 4, index: 113122, name: "Paulina", surname: "Mironiuk", email: "email@wp.pl", active: true},
    {id: 5, index: 145123, name: "Ola", surname: "Nowak", email: "email@wp.pl", active: true},
    {id: 6, index: 127622, name: "Kuba", surname: "Kowalski", email: "email@wp.pl", active: true},
    {id: 7, index: 223134, name: "Tomek", surname: "Nowak", email: "email@wp.pl", active: true},
    {id: 8, index: 145123, name: "Ola", surname: "Nowak", email: "email@wp.pl", active: true},
    {id: 9, index: 127622, name: "Kuba", surname: "Kowalski", email: "email@wp.pl", active: true},
    {id: 10, index: 223134, name: "Tomek", surname: "Nowak", email: "email@wp.pl", active: true},
    {id: 1, index: 123123, name: "Mateusz", surname: "Wedeł", email: "email@wp.pl", active: true},
    {id: 2, index: 123122, name: "Mateusz", surname: "Smagieł", email: "email@wp.pl", active: true},
    {id: 3, index: 223122, name: "Dawid", surname: "Majorczyk", email: "email@wp.pl", active: true},
    {id: 4, index: 113122, name: "Paulina", surname: "Mironiuk", email: "email@wp.pl", active: true},
    {id: 5, index: 145123, name: "Ola", surname: "Nowak", email: "email@wp.pl", active: true},
    {id: 6, index: 127622, name: "Kuba", surname: "Kowalski", email: "email@wp.pl", active: true},
    {id: 7, index: 223134, name: "Tomek", surname: "Nowak", email: "email@wp.pl", active: true},
    {id: 8, index: 145123, name: "Ola", surname: "Nowak", email: "email@wp.pl", active: true},
    {id: 9, index: 127622, name: "Kuba", surname: "Kowalski", email: "email@wp.pl", active: true},
    {id: 10, index: 223134, name: "Tomekaaa", surname: "Nowak", email: "email@wp.pl", active: true}
  ]

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  users: User[] = [];
  pageable: Pageable = {page: 0, size: 10, sort: 'surname', direction: 'DESC'};
  pageSizeOptions = [10, 25, 50];
  totalUsers: number;
  displayedColumns: string[] = ['name', 'surname', 'index', 'email', 'active', 'block'];
  //dataSource = new MatTableDataSource<User>(this.usersMock);

  constructor(private userService: UserService) { }

  ngOnInit() {
    //this.dataSource.paginator = this.paginator;
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page).subscribe(() => {
      this.pageable.page = this.paginator.pageIndex;
      this.pageable.size = this.paginator.pageSize;
    });
  }

  private getUsers() {
    this.userService.getUsers(this.pageable)
    .subscribe(data => {
      this.users = data.content;
      this.totalUsers = data.totalElements;
    })
  }

  private changeUserStatus(email: string) {
    this.userService.changeUserStatus(email)
      .subscribe(() => {
        this.reload();
        console.log("User status changed");
      })
  }

  private reload() {
    this.getUsers();
  }

}
