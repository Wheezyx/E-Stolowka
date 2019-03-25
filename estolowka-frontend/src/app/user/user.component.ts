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

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  users: User[] = [];
  pageable: Pageable = {page: 0, size: 10, sort: 'surname', direction: 'DESC'};
  pageSizeOptions = [10, 25, 50];
  totalUsers: number;
  displayedColumns: string[] = ['name', 'surname', 'index', 'email', 'active', 'block'];

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.getUsers();
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
