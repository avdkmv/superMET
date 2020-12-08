import { Injectable } from "@angular/core"
import { Subject } from 'rxjs'

@Injectable({
    providedIn: "root",
})
export class MessagingService {

    private loginMessageSource = new Subject<boolean>()

    loginMessage$ = this.loginMessageSource.asObservable()

    constructor() {}

    login() {
        this.loginMessageSource.next(true)
    }

    logout() {
        this.loginMessageSource.next(false)
    }
}
