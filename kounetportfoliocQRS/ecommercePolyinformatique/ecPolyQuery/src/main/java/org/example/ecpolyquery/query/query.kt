package org.example.ecpolyquery.query

public class GetAllCategoriesQuery {
}

public class GetAllCustomersQuery {
}

public class GetAllInvoicesQuery {
}

public class GetAllOrdersQuery {
}

public class GetAllOrderLinesQuery {
}

public class GetAllProductsQuery(
  var page: Int = 0,
  var size: Int = 10,
  categoryId: String,
  couleurs: String,
  socialGroupId: String,
  productSize: String
)

public class GetAllProductSizesQuery {
}

public class GetAllPurchasesQuery {
}

public class GetAllPurchaseItemsQuery {
}

public class GetAllShippingsQuery {
}

public class GetAllSocialGroupsQuery {
}

public class GetAllStocksQuery {
}

public class GetAllSubcategoriesQuery {
}

public class GetAllSuppliersQuery(
    var page: Int = 0,
    var size: Int = 10
)

class GetCategoryByIdQuery(val id: String)

class GetCustomerByIdQuery(val id: String)

class GetInvoiceByIdQuery(val id: String)

class GetOrderByIdQuery(val id: String)

class GetOrderLineByIdQuery(val id: String)

class GetProductByIdQuery(val id: String)

class GetProductSizeByIdQuery(val id: String)

class GetPurchaseByIdQuery(val id: String)

class GetPurchaseItemByIdQuery(val id: String)

class GetShippingByIdQuery(val id: String)

class GetSocialGroupByIdQuery(val id: String)

class GetStockByIdQuery(val id: String)

class GetSubcategoryByIdQuery(val id: String)

class GetSupplierByIdQuery(val id: String)
