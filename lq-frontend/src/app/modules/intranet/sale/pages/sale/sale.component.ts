import { Component, OnInit } from '@angular/core';
import { SaleService } from 'src/app/core/services/sales/sales.service';
import { TypeFilterDateSales } from 'src/app/core/utilities/utilities-interfaces';
import { format, subDays } from 'date-fns';
import { NotificationService } from 'src/app/core/services/notification/notification.service';
import {
  SalesDaily,
  SalesMonth,
  SalesWeek,
} from 'src/app/core/models/sales/sales.interface';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-sale',
  templateUrl: './sale.component.html',
  styleUrls: ['./sale.component.css'],
})
export class SaleComponent implements OnInit {
  loadingSale: boolean = false;
  typesCardsSales = TypeFilterDateSales;
  indexCardName: string = this.typesCardsSales._05_TOTAL_;
  sales: number = 0;
  salesDay!: SalesDaily;
  salesWeek!: SalesWeek;
  salesMoth!: SalesMonth;
  salesFilter: number = 0;
  dateRangeForm!: FormGroup;
  visibleFilter: boolean = false;

  constructor(
    private saleService: SaleService,
    private notificationService: NotificationService,
    private fb: FormBuilder
  ) {
    this.getTotalSale();
  }

  ngOnInit(): void {
    this.dateRangeForm = this.fb.group({
      startDate: ['', [Validators.required]], // Fecha inicial (15 días antes)
      endDate: ['', [Validators.required]], // Fecha final (hoy)
    });
  }

  getTotalSale() {
    this.visibleFilter = false;
    this.loadingSale = true;
    this.indexCardName = this.typesCardsSales._05_TOTAL_;
    this.saleService.getSale().subscribe((res) => {
      this.loadingSale = false;
      if (res) {
        this.sales = res;
      }
    });
  }

  getDaySales() {
    this.visibleFilter = false;
    const today = new Date();
    const formattedDate = format(today, 'yyyy-MM-dd');
    this.loadingSale = true;
    this.indexCardName = this.typesCardsSales._01_VENTA_DIARIA_;
    this.saleService.getSaleDaily(formattedDate).subscribe(
      (res) => {
        if (res) {
          this.salesDay = res;
        }
        this.loadingSale = false;
      },
      (error) => {
        this.notificationService.error(
          'Ocurio un error al optener las ventas del dia'
        );
        this.loadingSale = true;
      }
    );
  }

  getWeekSales() {
    this.visibleFilter = false;
    const today = new Date();
    const oneWeekAgo = new Date(today);
    oneWeekAgo.setDate(today.getDate() - 7);

    const formattedOneWeekAgo = format(oneWeekAgo, 'yyyy-MM-dd');

    this.loadingSale = true;
    this.indexCardName = this.typesCardsSales._02_VENTA_SEMANAL_;
    this.saleService.getSaleWeek(formattedOneWeekAgo).subscribe(
      (res) => {
        if (res) {
          this.salesWeek = res; // Asigna la respuesta a la propiedad correspondiente
        }
        this.loadingSale = false;
      },
      (error) => {
        this.notificationService.error(
          'Ocurrió un error al obtener las ventas de la semana'
        );
        this.loadingSale = false;
      }
    );
  }

  getMonthSales() {
    this.visibleFilter = false;
    const today = new Date();
    const oneMonthAgo = new Date(today);
    oneMonthAgo.setMonth(today.getMonth() - 1);

    const formattedOneMonthAgo = format(oneMonthAgo, 'yyyy-MM-dd');

    this.loadingSale = true;
    this.indexCardName = this.typesCardsSales._03_VENTA_MENSUAL_;
    this.saleService.getSaleMonth(formattedOneMonthAgo).subscribe(
      (res) => {
        if (res) {
          this.salesMoth = res; // Asigna la respuesta a la propiedad correspondiente
        }
        this.loadingSale = false;
      },
      (error) => {
        this.notificationService.error(
          'Ocurrió un error al obtener las ventas del mes'
        );
        this.loadingSale = false;
      }
    );
  }

  getRankSales() {
    this.loadingSale = true;
    this.indexCardName = this.typesCardsSales._04_RANGOS_;
    let body = {
      startDate: this.dateRangeForm.controls['startDate'].value,
      endDate: this.dateRangeForm.controls['endDate'].value,
    };
    this.saleService.getSalesByRange(body.startDate, body.endDate).subscribe(
      (res) => {
        if (res) {
          this.salesFilter = res;
        } else {
          this.notificationService.info('No se encontraron ventas');
        }
        this.loadingSale = false;
      },
      (error) => {
        this.notificationService.error(error.detail.detail);
        this.loadingSale = false;
      }
    );
  }

  activatedFilters() {
    this.visibleFilter = true;
    this.indexCardName = this.typesCardsSales._04_RANGOS_;
  }
}
