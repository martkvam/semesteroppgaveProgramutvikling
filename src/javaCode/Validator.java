package javaCode;

import javaCode.Dialogs;
import javaCode.InLog.ReadUsers;
import javaCode.InLog.User;
import javaCode.Lists;
import javaCode.superUser.addElements;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.IllegalFormatException;
//If new user is incorrect, e.g. name is numbers the Exception will be thrown but the user still registers.
//Need to stop the user from registering and "try again"
public class Validator {
    public static String name(String name) {
        try{
            if (!name.matches("([a-zA-ZæøåÆØÅ]+['\\-,. ]?)+")){
                throw new IllegalArgumentException("Invalid name");
            } else {
                return name;
            }
        } catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
            return null;
        }
    }

    public static String phone(String phone){
        try{
            if (!phone.matches("(([(][+]{0,1})|([+]?))[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*")){
                throw new IllegalArgumentException("Invalid phone number");
            } else{
                return phone;
            }
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
            return null;
        }
    }

    public static String email(String email){
        try{
            if(!email.matches(".[\\S]+@.[\\S]+[.].[\\S]+")){
                throw new IllegalArgumentException("Invalid email");
            } else {
                return email;
            }
        }catch (IllegalArgumentException e){
            Dialogs.showErrorDialog(e.getMessage());
            return null;
        }
    }


    //Input validation for car objects
    public static boolean carId(String id){
        int idCounter = 0;
        if(id.matches("[0-9]+")){
            for(int i = 0; i < Lists.getCars().size(); i++){
                if(idCounter<=Integer.parseInt(Lists.getCars().get(i).getCarID())){
                    idCounter=Integer.parseInt(Lists.getCars().get(i).getCarID());
                }
            }
            if(idCounter >= Integer.parseInt(id)){
                return false;
            }
            else {
                return true;
            }
        }
        else{
            return false;
        }
    }

    public static boolean carType(String type){
        return type.matches(".{2,10}");
    }

    public static boolean carDescription(String description){
        return description.matches(".{2,50}");
    }

    public static boolean carPrice(int price){
        return price>0 && price<1_000_000;
    }


    //Components
    public static boolean carIdComponents(String ID){
        for(int i = 0; i < Lists.getCars().size(); i++){
            if(Lists.getCars().get(i).getCarID().equals(ID)){
                return true;
            }
        }
        return false;
    }
    public static boolean componentId(String ID){
        String[] splitInnId = ID.split("-");
        int highestcomponentType = 0;
        int highestcomponentTypeId = 0;

        for(int i = 0; i < Lists.getComponents().size();i++){
            String [] splitExistingId = Lists.getComponents().get(i).getComponentID().split("-");
            String componentId = Lists.getComponents().get(i).getComponentID();
            if(componentId.equals(ID)){
                return false;
            }
            else if(Integer.parseInt(splitInnId[0])<0){
                return false;
            }
            else{
                if(splitInnId[0].equals(splitExistingId[0])){
                    //Finds highest componentId after "-" in component id-number
                    for(int j = 0; j <Lists.getComponents().size();j++){
                        if(splitInnId[0].equals(Lists.getComponents().get(j).getComponentID().charAt(0))){
                            if(highestcomponentTypeId <= Integer.parseInt(splitExistingId[1])){
                                highestcomponentTypeId = Integer.parseInt(splitExistingId[1]);
                            }
                        }
                    }
                    if(Integer.parseInt(splitInnId[1]) > highestcomponentTypeId){
                        return true;
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    public static boolean componentType(String type){
        if(type.length()==0){
            throw new IllegalArgumentException("The components type is not defined");
        }
        else{
            for(Component i : Lists.getComponents()){
                if(i.getComponentType().equals(type)){
                    return true;
                }
            }
            if(Dialogs.showChooseDialog("This component type is not defined. Do you want to add a new component?")){
                addElements.openAddComponentsDialog(Lists.getCars(), Lists.getComponents(),"", "", "", 0);
                return false;
            }
        }
        return true;
    }
    public static boolean componentPrice(int price){
        return price>0;
    }


    //Orders
    public static boolean orderNr(String nr){
        if(Integer.parseInt(nr) <= 0){
            return false;
        }
        for (Order i : Lists.getOrders()){
            if(i.getOrderNr().equals(nr)){
                return false;
            }
        }
        return true;
    }
    public static boolean orderPersonId(int id) throws FileNotFoundException {
        for(User i : ReadUsers.getUserList()){
            if(i.getId() == id){
                return true;
            }
        }
        return false;
    }

    public static boolean orderCarID(String carId){
        for(int i = 0; i < Lists.getCars().size(); i++){
            if(Lists.getCars().get(i).getCarID().equals(carId)){
                return true;
            }
        }
        return false;
    }

    public static boolean orderTotalPrice(int price){
        return price>0;
    }

    public static boolean orderCarColor(String color){
        return true;
    }
}
