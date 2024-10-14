export interface IngredientType {
  ingredientTypeId: number;
  name: string;
  active: boolean;
}

export interface Ingredient {
  ingredientId: number;
  ingredientType: IngredientType;
  name: string;
}

export interface DetailProduct {
  idDetailProduct: number;
  idIngredient: number;
  idProduct: number;
  quantity: number;
  ingredient: Ingredient;
}

export interface DetailAdditional {
  idDetailAdditional: number;
  idDetailOrder: number;
  idIngredient: number;
  ingredient: Ingredient;
}

export interface Product {
  idProduct: number;
  size: number;
  name: string;
  description: string;
  value: number;
  quantityClasic: number;
  quantityPremium: number;
  quantitySalsa: number;
  detailProduct: DetailProduct[];
  toppings: {
      classic: Ingredient[];
      premium: Ingredient[];
      sauces: Ingredient[];
      adicionales: Ingredient[];
      iceCream: Ingredient[];
  };
}


export interface DetailOrder {
  idDetailOrder: number;
  idOrder: number;
  product: Product;
  nameCustomer: string;
  quantity: number;
  value: number;
  observation: string
  detailAdditionals: DetailAdditional[];
}

export interface OrderResponse {
  idOrder: number;
  idUser: number;
  creationDate: string;
  total: number;
  status: string;
  discount: number | null;
  detailOrders: DetailOrder[];
}
