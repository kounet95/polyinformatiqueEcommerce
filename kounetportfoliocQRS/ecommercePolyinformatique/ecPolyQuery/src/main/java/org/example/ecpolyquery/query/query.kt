package org.example.ecpolyquery.query

import org.example.ecpolyquery.entity.ProductSize
import org.example.polyinformatiquecoreapi.dtoEcommerce.SizeProd
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import java.time.LocalDateTime
import java.util.*


public class GetAllCategoriesQuery {
}

public class GetAllAddressQuery {
}

public class GetAllCustomersQuery {
}

public class GetAllInvoicesQuery {
}

public class GetAllOrdersQuery {
}

public class GetAllOrderLinesQuery {
}

data class GetAllProductsQuery(
  val page: Int = 0,
  val size: Int = 10
)

data class GetAllProductsSizeQuery(
  val page: Int = 0,
  val size: Int = 10
)

data class GetAllSupplyQuery(
  val page: Int = 0,
  val size: Int = 10
)

data class GetAllSupplieQuery(
  val page: Int = 0,
  val size: Int = 10
)
data class GetAllProductSizesQuery(
  val page: Int = 0,
  val size: Int = 10,
  val selectedPrice: Int = 0,
  val selectedPricePromo: Int = 0,
  val prodsize: SizeProd? = null,
  val sortOption: String? = null  //
) {
  /**
   * Convertit le champ sortOption (ex : "price,asc" ou "name,desc") en objet Sort utilisable par Spring Data JPA.
   */
  fun getSortOptionAsSortSize(): Sort {
    if (sortOption.isNullOrBlank()) {
      return Sort.unsorted()
    }
    val parts = sortOption.split(",")
    return if (parts.size == 2) {
      val property = parts[0].trim()
      val direction = parts[1].trim()
      if (direction.equals("desc", ignoreCase = true)) {
        Sort.by(property).descending()
      } else {
        Sort.by(property).ascending()
      }
    } else {
      Sort.unsorted()
    }
  }
}
data class GetAllProductSizesByQuery(
  val id: String,
)

public class GetAllShippingsQuery {
}

public class GetAllSocialGroupsQuery (
  val page: Int = 0,
  val size: Int = 10,
  val categoryId: String? = null)




public class GetAllStocksQuery {
}

public class GetAllSubcategoriesQuery {
}
 class GetAllSuppliersQuery(
    var page: Int = 0,
    var size: Int = 10
)
data class SearchProductSizesQuery(
  val productName: String? = null,
  val minPromo: Double? = null,
  val maxPromo: Double? = null,
  val size: String? = null,
  val sale: Boolean? = null,
  val newSince: LocalDateTime? = null,
  val couleurs: List<String>? = null,
  val subcategoryId: String? = null,
  val socialGroupId: String? = null
)
class GetCategoryByIdQuery(val id: String)
class GetAddressByIdQuery(val id: String)
class FindOrderSummaryQuery (val orderId: String)
data class OrderSummary(
  var orderId: String = "",
  var customerId: String = "",
  var status: String = ""
)

class GetCustomerByIdQuery(val id: String)

class GetInvoiceByIdQuery(val id: String)

class GetOrderByIdQuery(val id: String)

class GetOrderLineByIdQuery(val id: String)

class GetProductByIdQuery(val id: String)
class GetProductSizeByIdQuery(val id: String)
class GetProductsBySousCategoryQuery (val sousCategoryId: String)
class findAllNewsProducts(date: Date?) {
  var date: Date?
    get() = date
  init {
    this.date = date
  }
}
data class GetNewArrivalsStockQuery(
  val since: LocalDateTime
)

data class GetSubcategoriesByCategoryIdQuery(
  val categoryIds: String
)

class findAllSaleProducts( val id: String)

class GetLikesByProductQuery( val productId: String)

class CountLikesByProductQuery ( val productId: String)

class CheckCustomerLikedProductQuery (
  val productId: String,
  val customerId: String)

class GetOnSaleStockQuery ( )




class GetPurchaseByIdQuery(val id: String)

class GetPurchaseItemByIdQuery(val id: String)

class GetShippingByIdQuery(val id: String)

class GetSocialGroupByIdQuery(val id: String)

class GetStockByIdQuery(val id: String)

class GetSubcategoryByIdQuery(val id: String)

class GetSupplierByIdQuery(val id: String)

class GetAddressesByCustomerIdQuery(val customerId: String)
