import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
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
export class UserComponent implements OnInit, AfterViewInit {

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  pageable: Pageable = { page: 0, size: 10, sort: 'surname', direction: 'ASC' };
  pageSizeOptions = [10];
  totalUsers: number;
  displayedColumns: string[] = ['name', 'surname', 'index', 'email', 'active', 'block'];
  dataSource = new MatTableDataSource<User>();

  constructor(private userService: UserService) { }

  ngOnInit() {
    this.getUsers();
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page).subscribe(() => {
      this.pageable.page = this.paginator.pageIndex;
      this.pageable.size = this.paginator.pageSize;
      this.pageable.sort = this.sort.active;
      this.pageable.direction = this.sort.direction;
    });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  private getUsers() {
    this.userService.getUsers(this.pageable)
      .subscribe(data => {
        this.dataSource = new MatTableDataSource<User>(data.content);
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
