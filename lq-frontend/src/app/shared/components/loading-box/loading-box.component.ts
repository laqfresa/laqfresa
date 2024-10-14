import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-loading-box',
  templateUrl: './loading-box.component.html',
  styleUrls: ['./loading-box.component.css']
})
export class LoadingBoxComponent {

  @Input() loadingText!: string;

 constructor(){}


}
