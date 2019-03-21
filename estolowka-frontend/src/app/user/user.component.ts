import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator, MatSort, MatTableDataSource } from '@angular/material';
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
    {index: 123123, name: "Mateusz", surname: "Wedeł", email: "email@wp.pl", enabled: false},
    {index: 123122, name: "Mateusz", surname: "Smagieł", email: "email@wp.pl", enabled: true},
    {index: 223122, name: "Dawid", surname: "Majorczyk", email: "email@wp.pl", enabled: true},
    {index: 113122, name: "Paulina", surname: "Mironiuk", email: "email@wp.pl", enabled: true},
    {index: 145123, name: "Ola", surname: "Nowak", email: "email@wp.pl", enabled: true},
    {index: 127622, name: "Kuba", surname: "Kowalski", email: "email@wp.pl", enabled: true},
    {index: 223134, name: "Tomek", surname: "Nowak", email: "email@wp.pl", enabled: true},
    {index: 145123, name: "Ola", surname: "Nowak", email: "email@wp.pl", enabled: true},
    {index: 127622, name: "Kuba", surname: "Kowalski", email: "email@wp.pl", enabled: true},
    {index: 223134, name: "Tomek", surname: "Nowak", email: "email@wp.pl", enabled: true},
    {index: 123123, name: "Mateusz", surname: "Wedeł", email: "email@wp.pl", enabled: true},
    {index: 123122, name: "Mateusz", surname: "Smagieł", email: "email@wp.pl", enabled: true},
    {index: 223122, name: "Dawid", surname: "Majorczyk", email: "email@wp.pl", enabled: true},
    {index: 113122, name: "Paulina", surname: "Mironiuk", email: "email@wp.pl", enabled: true},
    {index: 145123, name: "Ola", surname: "Nowak", email: "email@wp.pl", enabled: true},
    {index: 127622, name: "Kuba", surname: "Kowalski", email: "email@wp.pl", enabled: true},
    {index: 223134, name: "Tomek", surname: "Nowak", email: "email@wp.pl", enabled: true},
    {index: 145123, name: "Ola", surname: "Nowak", email: "email@wp.pl", enabled: true},
    {index: 127622, name: "Kuba", surname: "Kowalski", email: "email@wp.pl", enabled: true},
    {index: 223134, name: "Tomekaaa", surname: "Nowak", email: "email@wp.pl", enabled: true}
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
    this.getUsers();
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

  blockMockedUser(user: User) {
    user.enabled = false;
    console.log("User blocked");
  }

  activateMockedUser(user: User) {
    user.enabled = true;
    console.log("User activated");
  }

  private reload() {
    this.getUsers();
  }

}
