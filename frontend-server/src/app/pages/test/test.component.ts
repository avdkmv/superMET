import { Component, OnInit } from '@angular/core';
import { MessagingService } from 'src/app/services/messaging.service';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.sass']
})
export class TestComponent implements OnInit {

  constructor(private msgService: MessagingService) {
      this.msgService.loginMessage$.subscribe()
  }

  ngOnInit(): void {
  }

}
