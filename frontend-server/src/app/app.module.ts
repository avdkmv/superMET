import { BrowserModule } from "@angular/platform-browser"
import { NgModule } from "@angular/core"

import { AppRoutingModule } from "./app-routing.module"
import { AppComponent } from "./app.component"
import { NgbModule } from "@ng-bootstrap/ng-bootstrap"
import { TestComponent } from "./pages/test/test.component"
import { HeaderComponent } from "./components/header/header.component"
import { FooterComponent } from "./components/footer/footer.component"
import { UsersComponent } from "./pages/test/users/users.component"
import { LoginFormComponent } from "./components/login-form/login-form.component"
import { ReactiveFormsModule } from "@angular/forms"
import { BrowserAnimationsModule } from "@angular/platform-browser/animations"
import { ToastrModule } from "ngx-toastr"
import { CookieService } from "ngx-cookie-service"

@NgModule({
    declarations: [
        AppComponent,
        TestComponent,
        HeaderComponent,
        FooterComponent,
        UsersComponent,
        LoginFormComponent,
        LoginFormComponent,
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        ReactiveFormsModule,
        BrowserAnimationsModule,
        ToastrModule.forRoot(),
        NgbModule,
    ],
    providers: [CookieService],
    bootstrap: [AppComponent],
})
export class AppModule {}
