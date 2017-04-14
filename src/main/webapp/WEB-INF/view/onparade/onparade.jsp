<c:import url="${viewPrefix}/common/default_layout.jsp">
<c:param name="contents">
<!-- <main> start main content -->
<div class="contents">
	<h2 class="sg-content-title"><la:caption key="labels.title.product.list"/></h2>
	<section class="product-search-box">
		<h3 class="sg-content-title-second">Search Condition</h3>
		<la:form styleClass="product-search-form">
			<la:errors/>
			<ul class="product-search-condition-list">
				<li><span>Name</span><la:text property="productName"/></li>
				<li><span>Purchased Member</span><la:text property="purchaseMemberName"/></li>
				<li>
					<span><la:caption key="labels.product.status"/></span> 
					<la:select property="productStatus">
						<la:option value="" key="labels.listbox.caption.tell"/>
						<la:optionsCls name="ProductStatus"/>
					</la:select>
				</li>
			</ul>
			<la:submit value="labels.search"/>
			<la:text property="nested.nestedStr"/>
			<la:text property="nestedList[0].nestedStr"/>
			<la:select property="fields.label" multiple="true">
				<la:option value="" key="labels.listbox.caption.tell"/>
				<la:optionsCls name="ProductStatus"/>
			</la:select>
		</la:form>
	</section>
	<section class="product-result-box">
		<h3 class="sg-content-title-second">Search Results</h3>
		<table class="product-list-tbl">
			<thead>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Price</th>
					<th>Category</th>
					<th>Registration</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="bean" items="${beans}">
				<tr>
					<td>${f:h(bean.productId)}</td>
					<td>${f:h(bean.productName)}</td>
					<td>${f:h(bean.regularPrice)}</td>
					<td></td>
					<td></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<section class="product-list-paging-box">
			<c:import url="${viewPrefix}/common/paging_navi.jsp"/>
		</section>
	</section>
</div>
<!-- </main> end of main content -->
</c:param>
</c:import>
