package org.nearbyshops.communitylibrary.RESTInterfaces;

import org.nearbyshops.communitylibrary.Globals.Globals;
import org.nearbyshops.communitylibrary.Model.Book;
import org.nearbyshops.communitylibrary.ModelEndpoint.BookEndpoint;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

/**
 * Created by sumeet on 9/8/16.
 */

@Path("/v1/Book")
public class BookResource {

        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response saveBook(Book book)
        {
            int idOfInsertedRow = Globals.bookDAO.saveBook(book);

            book.setBookID(idOfInsertedRow);

            if(idOfInsertedRow >=1)
            {
                Response response = Response.status(Response.Status.CREATED)
                        .location(URI.create("/api/Book/" + idOfInsertedRow))
                        .entity(book)
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
        @Path("/{BookID}")
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response updateItem(Book book, @PathParam("BookID")int bookID)
        {

            book.setBookID(bookID);

            int rowCount = Globals.bookDAO.updateBook(book);

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
        public Response updateItemBulk(List<Book> bookList)
        {
            int rowCountSum = 0;

            for(Book item : bookList)
            {
                rowCountSum = rowCountSum + Globals.bookDAO.updateBook(item);
            }

            if(rowCountSum ==  bookList.size())
            {
                Response response = Response.status(Response.Status.OK)
                        .entity(null)
                        .build();

                return response;
            }
            else if( rowCountSum < bookList.size() && rowCountSum > 0)
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
        @Path("/{BookID}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response deleteItem(@PathParam("BookID")int bookID)
        {

            int rowCount = Globals.bookDAO.deleteBook(bookID);


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
        public Response getBooks(
                @QueryParam("BookCategoryID")Integer bookCategoryID,
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

            BookEndpoint endPoint = Globals.bookDAO.getEndPointMetadata(bookCategoryID);

            endPoint.setLimit(set_limit);
            endPoint.setMax_limit(max_limit);
            endPoint.setOffset(set_offset);

            List<Book> list = null;


            if(metaonly==null || (!metaonly)) {

                list =
                        Globals.bookDAO.getBooks(
                                bookCategoryID,
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
        @Path("/{BookID}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getItem(@PathParam("BookID")int bookID)
        {
            System.out.println("BookID=" + bookID);


            //marker

            Book item = Globals.bookDAO.getBook(bookID);

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
