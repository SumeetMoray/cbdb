package org.nearbyshops.communitylibrary.RESTEndpoint;

import org.nearbyshops.communitylibrary.Globals.Globals;
import org.nearbyshops.communitylibrary.Model.BookCategory;
import org.nearbyshops.communitylibrary.ModelEndpoint.BookCategoryEndpoint;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by sumeet on 9/8/16.
 */

@Path("/v1/BookCategory")
public class BookCategoryResource {

        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response saveBookCategory(BookCategory bookCategory)
        {
            int idOfInsertedRow = Globals.bookCategoryDAO.saveBookCategory(bookCategory);

            bookCategory.setBookCategoryID(idOfInsertedRow);

            if(idOfInsertedRow >=1)
            {
                Response response = Response.status(Response.Status.CREATED)
                        .location(URI.create("/api/v1/BookCategory/" + idOfInsertedRow))
                        .entity(bookCategory)
                        .build();

                return response;

            }else if(idOfInsertedRow <= 0)
            {
                Response response = Response.status(Response.Status.NOT_MODIFIED)
                        .entity(null)
                        .build();

                return response;
            }


            return null;
        }


        @PUT
        @Path("/{BookCategoryID}")
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response updateBookCategory(BookCategory bookCategory, @PathParam("BookCategoryID")int bookCategoryID)
        {

            bookCategory.setBookCategoryID(bookCategoryID);

            int rowCount = Globals.bookCategoryDAO.updateBookCategory(bookCategory);

            if(rowCount >= 1)
            {
                Response response = Response.status(Response.Status.OK)
                        .entity(null)
                        .build();

                return response;
            }
            if(rowCount == 0)
            {
                Response response = Response.status(Response.Status.NOT_MODIFIED)
                        .entity(null)
                        .build();

                return response;
            }

            return null;

        }



        @PUT
        @Consumes(MediaType.APPLICATION_JSON)
        public Response updateBookCategoryBulk(List<BookCategory> bookCategoryList)
        {
            int rowCountSum = 0;

            for(BookCategory item : bookCategoryList)
            {
                rowCountSum = rowCountSum + Globals.bookCategoryDAO.updateBookCategory(item);
            }

            if(rowCountSum ==  bookCategoryList.size())
            {
                Response response = Response.status(Response.Status.OK)
                        .entity(null)
                        .build();

                return response;
            }
            else if( rowCountSum < bookCategoryList.size() && rowCountSum > 0)
            {
                Response response = Response.status(Response.Status.PARTIAL_CONTENT)
                        .entity(null)
                        .build();

                return response;
            }
            else if(rowCountSum == 0 ) {

                Response response = Response.status(Response.Status.NOT_MODIFIED)
                        .entity(null)
                        .build();

                return response;
            }

            return null;
        }



        @DELETE
        @Path("/{BookCategoryID}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response deleteBookCategory(@PathParam("BookCategoryID")int bookCategoryID)
        {

            int rowCount = Globals.bookCategoryDAO.deleteBookCategory(bookCategoryID);

            if(rowCount>=1)
            {
                Response response = Response.status(Response.Status.OK)
                        .entity(null)
                        .build();

                return response;
            }

            if(rowCount == 0)
            {
                Response response = Response.status(Response.Status.NOT_MODIFIED)
                        .entity(null)
                        .build();

                return response;
            }

            return null;
        }



        @GET
        @Produces(MediaType.APPLICATION_JSON)
        public Response getBookCategories(
                @QueryParam("ParentCategoryID")Integer parentCategoryID,
                @QueryParam("SortBy") String sortBy,
                @QueryParam("Limit")Integer limit, @QueryParam("Offset")Integer offset,
                @QueryParam("metadata_only")Boolean metaonly)
        {

            int set_limit = 30;
            int set_offset = 0;
            final int max_limit = 100;

            if(limit!= null)
            {

                if (limit >= max_limit) {

                    set_limit = max_limit;
                }
                else
                {

                    set_limit = limit;
                }

            }

            if(offset!=null)
            {
                set_offset = offset;
            }

            BookCategoryEndpoint endPoint = Globals.bookCategoryDAO.getEndPointMetadata(parentCategoryID);

            endPoint.setLimit(set_limit);
            endPoint.setMax_limit(max_limit);
            endPoint.setOffset(set_offset);

            List<BookCategory> list = null;


            if(metaonly==null || (!metaonly)) {

                list =
                        Globals.bookCategoryDAO.getBookCategories(
                                parentCategoryID,
                                sortBy,set_limit,set_offset
                        );

                endPoint.setResults(list);
            }


            //Marker

            return Response.status(Response.Status.OK)
                    .entity(endPoint)
                    .build();

        }





        @GET
        @Path("/{BookCategoryID}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getItem(@PathParam("BookCategoryID")int bookCategoryID)
        {
            System.out.println("BookID=" + bookCategoryID);


            //marker

            BookCategory item = Globals.bookCategoryDAO.getBookCategory(bookCategoryID);

            if(item!= null)
            {
                Response response = Response.status(Response.Status.OK)
                        .entity(item)
                        .build();

                return response;

            } else
            {

                Response response = Response.status(Response.Status.NO_CONTENT)
                        .entity(item)
                        .build();

                return response;

            }
        }

}
