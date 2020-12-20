import { Component, OnInit } from "@angular/core"

@Component({
    selector: "app-doctor",
    templateUrl: "./doctor.component.html",
    styleUrls: ["./doctor.component.sass"],
})
export class DoctorComponent implements OnInit {
    testdata = [
        {
            id: 1,
            name: "Facility 1",
            description: "Random descr 1",
        },
        {
            id: 2,
            name: "Facility 2",
            description: "Random descr 2",
        },
        {
            id: 3,
            name: "Facility 3",
            description: "Random descr 3",
        },
    ]

    currentDate: Date
    totalDays: number

    constructor() {
        this.currentDate = new Date()
        this.totalDays = new Date(this.currentDate.getFullYear(), this.currentDate.getMonth(), 0).getDate() + 1
    }

    ngOnInit(): void {}
}
