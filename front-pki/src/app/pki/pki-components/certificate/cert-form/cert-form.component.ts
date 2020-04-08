import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cert-form',
  templateUrl: './cert-form.component.html',
  styleUrls: ['./cert-form.component.scss']
})
export class CertFormComponent implements OnInit {

  public existingCAs: Array<string> = ["New CA", "Root", "CA 1"];

  public selectedCA : string = "New CA";

  public displayedColumns: string[] = ['position', 'name', 'weight', 'symbol'];

  public dataSource;

  public data = [
    {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
    {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
    {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
    {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
    {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
    {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
    {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
    {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
    {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
    {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
  ];

  constructor() {
    this.selectedCA = "New CA";
    this.dataSource = this.data;
  }

  ngOnInit(): void {
  }


}
