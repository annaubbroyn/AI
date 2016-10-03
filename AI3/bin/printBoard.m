folder = 'C:\Users\Anna\Documents\GitHub\AI\AI3\solutions\';
name = 'solution-1-4.txt';
fullname = [folder name];
data = importdata(fullname);
[rows,m] = size(data);
disp(data{1,1});
line = strsplit(data{1,1},{', ','[',']'});
cols = length(line);
open = zeros(rows,cols);
obstical = zeros(rows,cols);
trace = zeros(rows,cols);
start = zeros(rows,cols);
final = zeros(rows,cols);
for i = 1:rows
    line = strsplit(data{i,1},{', ','[',']'});
    for j = 2:length(line)-1
        if(str2double(line{j}) == 0)
            open(i,j) = 1;
        elseif(str2double(line{j}) == 1)
            obstical(i,j) = 1;
        elseif(str2double(line{j}) == 2)
            trace(i,j) = 1;
        elseif(str2double(line{j}) == 3)
            start(i,j) = 1;
        elseif(str2double(line{j}) == 4)
            final(i,j) = 1;
        end
    end
end

figure(1)
hold on
spy(obstical,'sk',22)
markerOb = findall(gca,'color','k');
set(markerOb,'MarkerFaceColor','k');
spy(trace,'.')
spy(start,'or',12)
spy(final,'og',12)
markerSt = findall(gca,'color','r');
markerFi = findall(gca,'color','g');
set(markerSt,'MarkerFaceColor','r');
set(markerFi,'MarkerFaceColor','g');
axis([1.5 cols-0.5 0.5 rows+0.5])
box off;
set(gca,'xcolor',get(gcf,'color'));
set(gca,'ycolor',get(gcf,'color'));
set(gca,'xtick',[]);
set(gca,'ytick',[]);
set(gca,'Color',[0.9 0.9 0.9]);



